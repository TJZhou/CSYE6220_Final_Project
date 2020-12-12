import { BillGroup } from './../models/bill-group';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseWrapper } from '../models/response-wrapper';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private groupUrl = 'http://localhost:8080/api/group/';

  constructor(private http: HttpClient) { }

  // get one group based on groupId
  public getGroup(groupId: string): Observable<ResponseWrapper<BillGroup>> {
    return this.http.get<ResponseWrapper<BillGroup>>(this.groupUrl + 'one/' + groupId);
  }

  // get all groups a user participated in
  public getGroups(userId: number): Observable<ResponseWrapper<Array<BillGroup>>> {
    return this.http.get<ResponseWrapper<Array<BillGroup>>>(this.groupUrl + userId);
  }

  public createGroup(userId: number, billGroup: BillGroup): Observable<ResponseWrapper<string>> {
    return this.http.post<ResponseWrapper<string>>(this.groupUrl + userId, billGroup);
  }

  public joinGroup(userId: number, groupId: string): Observable<ResponseWrapper<void>> {
    return this.http.post<ResponseWrapper<void>>(this.groupUrl + userId + '/' + groupId, {});
  }

  public deleteGroup(userId: number, groupId: string): Observable<ResponseWrapper<void>> {
    return this.http.delete<ResponseWrapper<void>>(this.groupUrl + userId + '/' + groupId);
  }
}
