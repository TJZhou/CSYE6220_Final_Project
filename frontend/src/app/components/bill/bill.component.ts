import { BillGroup } from './../../models/bill-group';
import { GroupService } from './../../services/group.service';
import { BillService } from './../../services/bill.service';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtHelperService } from '@auth0/angular-jwt';
import { getTodayDate } from './../../utils/month-generate-util';

@Component({
  selector: 'app-bill',
  templateUrl: './bill.component.html',
  styleUrls: ['./bill.component.scss']
})
export class BillComponent implements OnInit {

  isLoading: boolean;
  userId: number;
  joinGroupId: string;
  createGroupName: string;
  billGroups: Array<BillGroup>;
  detailBillGroup: BillGroup;
  showGroupDetail: boolean;

  constructor(private jwtHelper: JwtHelperService,
              private billService: BillService,
              private groupService: GroupService,
              private errorMessage: MatSnackBar) {
    const token = this.jwtHelper.decodeToken(localStorage.getItem('access_token'));
    this.userId = parseInt(token.aud, 10);
    this.isLoading = false;
    this.showGroupDetail = false;
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.groupService.getGroups(this.userId).subscribe(resp => {
      this.billGroups = resp.data;
      this.isLoading = false;
    }, err => {
      this.errorHandling(err);
    });
  }

  public createGroup(): void {
    const billGroup: BillGroup = new BillGroup();
    billGroup.groupName = this.createGroupName;
    billGroup.createdAt = getTodayDate();
    this.isLoading = true;
    this.groupService.createGroup(this.userId, billGroup).subscribe(resp => {
      this.successRespHandling(resp);
    }, err => {
      this.errorHandling(err);
    });
  }

  public joinGroup(): void {
    this.groupService.joinGroup(this.userId, this.joinGroupId).subscribe(resp => {
      this.successRespHandling(resp);
    }, err => {
      this.errorHandling(err);
    });
  }

  public deleteGroup(): void {
    this.groupService.deleteGroup(this.userId, this.detailBillGroup.id).subscribe(resp => {
      this.successRespHandling(resp);
    }, err => {
      this.errorHandling(err);
    });
  }

  public showDetail(billGroup: BillGroup): void {
    this.detailBillGroup = billGroup;
    this.showGroupDetail = true;
  }

  public createBill(): void {

  }

  private successRespHandling(resp): void {
    this.isLoading = false;
    location.reload();
  }

  private errorHandling(err): void {
    console.log(err);
    this.errorMessage.open(err.error.message, 'Err', {
      duration: 5000,
    });
    this.isLoading = false;
  }

}
