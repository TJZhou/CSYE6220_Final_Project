import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BillService {

  private userUrl = 'http://localhost:8080/api/bill/';

  constructor(private http: HttpClient) { }
}
