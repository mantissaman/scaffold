import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { StorageService } from './storage.service';

const AUTH_API = 'http://localhost:8080/api/';

@Injectable({
  providedIn: 'root',
})
export class DataGatewayService {
  constructor(private http: HttpClient, private authService: AuthService, private storageService: StorageService) {}
  
  perform(query: string, model: any ): Observable<any> {
    return this.http.post(
      AUTH_API + 'get-data/'+query,
      model,
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': "Bearer " + this.storageService.getUser() })
      }
    );
  }

}