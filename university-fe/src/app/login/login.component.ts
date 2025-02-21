import { Component, OnInit } from '@angular/core';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService } from '@abacritt/angularx-social-login';
import { AuthService } from '../auth.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;
  socialLoginType = "google";

  constructor(
    private loginService: LoginService, 
    private authService: AuthService,
    private socialAuthService: SocialAuthService) { }

  ngOnInit(): void {
    this.socialAuthService.authState.subscribe((user) => {
      if(this.socialLoginType == "facebook") {

        this.loginService.loginWithFbToken(user.authToken)
          .subscribe(token => this.authService.saveToken(token));
        
      } else if (this.socialLoginType == "google") {
        this.loginService.loginWithGoogleToken(user.idToken)
          .subscribe(token => this.authService.saveToken(token));        
      }
    });
  }

  onLogin(){
    this.loginService.login(this.username, this.password)
        .subscribe(token => this.authService.saveToken(token));
  }

  onLogout(){
    this.authService.removeToken();
  }

  isLoggedIn(): boolean{
    return this.authService.getToken() != null;
  }

  facebookSignin(): void {
    this.socialLoginType = "facebook";
    this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID);
  }
}
