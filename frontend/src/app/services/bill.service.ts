import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bill } from '../models/bill';
import { ResponseWrapper } from '../models/response-wrapper';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BillService {

  private billUrl = environment.apiUrl + '/bill/';

  constructor(private http: HttpClient) { }

  public getBills(groupId: string): Observable<ResponseWrapper<Array<Bill>>> {
    return this.http.get<ResponseWrapper<Array<Bill>>>(this.billUrl + groupId);
  }

  public createBill(userId: number, groupId: string, bill: Bill): Observable<ResponseWrapper<number>> {
    return this.http.post<ResponseWrapper<number>>(this.billUrl + userId + '/' + groupId, bill);
  }

  public updateBillInfo(billId: number, bill: Bill): Observable<ResponseWrapper<void>> {
    return this.http.put<ResponseWrapper<void>>(this.billUrl + billId, bill);
  }

  public updateBillParticipants(groupId: string, billId: number, participants: object): Observable<ResponseWrapper<void>> {
    return this.http.put<ResponseWrapper<void>>(this.billUrl + groupId + '/' + billId, participants);
  }

  public deleteBill(groupId: string, billId: number): Observable<ResponseWrapper<void>> {
    return this.http.delete<ResponseWrapper<void>>(this.billUrl + groupId + '/' + billId);
  }
}
