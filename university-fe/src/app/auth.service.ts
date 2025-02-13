import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private token: string;

  private readonly tokenKey = 'jwt';

  constructor() { 
    this.token = null;
  }

  saveToken(newToken: string) {
    this.token = newToken;
    localStorage.setItem(this.tokenKey, newToken);
  } 

  getToken() {
    if(!this.token)
      this.token = localStorage.getItem(this.tokenKey);
    return this.token;
  }

  removeToken(){
    this.token = null;
    localStorage.removeItem(this.tokenKey);
  }
}
