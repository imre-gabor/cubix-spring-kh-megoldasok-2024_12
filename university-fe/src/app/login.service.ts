import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loginUrl = 'api/login';

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }

  login(username: string, password: string): Observable<string> {
    return this.http.post<string>(this.loginUrl, {
      "username": username,
      "password": password
    });
  } 

  loginWithFbToken(token: string): Observable<string> {
    return this.http.post<string>(this.loginUrl, {
      "fbToken": token
    });
  }

  loginWithGoogleToken(token: string): Observable<string> {
    return this.http.post<string>(this.loginUrl, {
      "googleToken": token
    });
  } 
}
