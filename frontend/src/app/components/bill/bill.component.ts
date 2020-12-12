import { Router, ActivatedRoute } from '@angular/router';
import { BillGroup } from './../../models/bill-group';
import { Bill } from '../../models/bill';
import { GroupService } from './../../services/group.service';
import { BillService } from './../../services/bill.service';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtHelperService } from '@auth0/angular-jwt';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-bill',
  templateUrl: './bill.component.html',
  styleUrls: ['./bill.component.scss']
})
export class BillComponent implements OnInit {

  isLoading: boolean;
  showAddBill: boolean;
  showDeleteBillButton: boolean;
  billTypes: string[];
  userId: number;
  groupId: string;
  billGroup: BillGroup;
  billSplit: Map<string, number>;
  bill: Bill;
  bills: Array<Bill>;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private billService: BillService,
              private groupService: GroupService,
              private errorMessage: MatSnackBar,
              private jwtHelper: JwtHelperService) {
    const token = this.jwtHelper.decodeToken(localStorage.getItem('access_token'));
    this.userId = parseInt(token.aud, 10);
    this.isLoading = true;
    this.showAddBill = false;
    this.showDeleteBillButton = false;
    this.bill = new Bill();
    this.billGroup = new BillGroup();
    this.billSplit = new Map();
    // in order to fix Cannot read property 'id' of undefined thrown in console
    this.billGroup.groupOwner = new User('', '', '', '');

    this.billTypes = ['Housing', 'Transportation', 'Food', 'Utilities', 'Clothing',
    'Healthcare', 'Insurance', 'Education', 'Entertainment', 'Other'];
    this.activatedRoute.queryParams.subscribe(params => {
      this.groupId = params.groupId;
    });
  }

  ngOnInit(): void {
    this.groupService.getGroup(this.groupId).subscribe(resp => {
      this.billGroup = resp.data;
      this.billGroup.groupParticipants.forEach(user => this.billSplit.set(user.username + '#' + user.id, 0));
      this.billService.getBills(this.billGroup.id).subscribe(resp2 => {
        this.bills = resp2.data;
        // calculate bills after splitting
        this.bills.forEach(bill => {
          const contributor = bill.userContributor;
          const contributorId = contributor.username + '#' + contributor.id;
          const participants = bill.userParticipants;
          this.billSplit.set(contributorId, this.billSplit.get(contributorId) + bill.amount);
          participants.forEach(participant => {
            const participantId = participant.username + '#' + participant.id;
            this.billSplit.set(participantId, this.billSplit.get(participantId) - bill.amount / participants.length);
          });
        });
        // round to 2 decimals, eg: 12.333333 to 12.33
        for (const key of this.billSplit.keys()) {
          this.billSplit.set(key, Math.round((this.billSplit.get(key) + Number.EPSILON) * 100) / 100);
        }
        this.isLoading = false;
      }, err => this.errorHandling(err));
    }, err =>  {
      this.errorHandling(err);
      this.router.navigateByUrl('group');
    });
  }

  public createBill(): void {
    this.showAddBill = true;
  }

  public back(): void {
    this.router.navigateByUrl('group');
  }

  public deleteGroup(): void {
    const confirmation = confirm('Are you sure you want to delete this group?');
    if (confirmation) {
      this.isLoading = true;
      this.groupService.deleteGroup(this.userId, this.billGroup.id).subscribe(resp => {
        this.isLoading = false;
        this.router.navigateByUrl('group');
      }, err => this.errorHandling(err));
    }
  }

  public save(): void {
    if (!this.checkFormField()) {
      this.errorMessage.open('Please fill all blank fields and check error information', 'Err', {
        duration: 5000,
      });
    } else {
      this.isLoading = true;
      if (this.bill.id !== null && this.bill.id !== undefined) { // if id is not null, then the state is "edit the bill"
        this.billService.updateBillInfo(this.bill.id, this.bill).subscribe(resp => {
          const participantsId = {participantsId: []};
          this.bill.userParticipants.forEach(participant => participantsId.participantsId.push(participant.id));
          this.billService.updateBillParticipants(this.billGroup.id, this.bill.id, participantsId).subscribe(resp2 => {
            location.reload();
          }, err => this.errorHandling(err));
        }, err => this.errorHandling(err));
      } else {
        this.billService.createBill(this.userId, this.billGroup.id, this.bill).subscribe(resp => {
          const billId = resp.data;
          const participantsId = {participantsId: []};
          this.bill.userParticipants.forEach(participant => participantsId.participantsId.push(participant.id));
          this.billService.updateBillParticipants(this.billGroup.id, billId, participantsId).subscribe(resp2 => {
            location.reload();
          }, err => this.errorHandling(err));
        }, err => this.errorHandling(err));
      }
    }
  }

  public edit(bill: Bill): void {
    this.showDeleteBillButton = true;
    this.showAddBill = true;
    this.bill = JSON.parse(JSON.stringify(bill));
  }

  public deleteBill(): void {
    this.billService.deleteBill(this.billGroup.id, this.bill.id).subscribe(resp => {
      location.reload();
    }, err => this.errorHandling(err));
  }

  // initialize form state
  public cancel(): void {
    this.showAddBill = false;
    this.isLoading = false;
    this.bill = new Bill();
    this.showDeleteBillButton = false;
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

  public checkFormField(): boolean {
    return this.bill.amount !== null && this.bill.amount !== undefined
        && this.bill.createdAt !== null && this.bill.createdAt !== undefined
        && this.bill.type !== null && this.bill.type !== undefined
        && this.bill.note !== null && this.bill.note !== undefined
        && this.bill.userParticipants !== null && this.bill.userParticipants !== undefined && this.bill.userParticipants.length > 0;
  }
}
