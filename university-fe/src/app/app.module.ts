import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { FacebookLoginProvider, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CourseChatComponent } from './course-chat/course-chat.component';
import { JwtInterceptor } from './jwt.interceptor';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    CourseChatComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    SocialLoginModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider(
              '894122598703614'
            )
          }
        ]
      } as SocialAuthServiceConfig
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
