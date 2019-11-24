import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppareilService } from './services/appareil.service';
import { ConnexionService } from './services/connexion.service';
import { ConnexionGuard } from './services/connexion-guard.service';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { ConnexionComponent } from './tests/connexion/connexion.component';
import { AppareilViewComponent } from './tests/appareil-view/appareil-view.component';
import { AppareilComponent } from './tests/appareil/appareil.component';
import { SingleAppareilComponent } from './tests/single-appareil/single-appareil.component';
import { TestSpringRestComponent } from './tests/test-spring-rest/test-spring-rest.component';
import { ErrorPage404Component } from './pages/error-page404/error-page404.component';
import { HeaderComponent } from './common/header/header.component';
import { FooterComponent } from './common/footer/footer.component';
import { SocialBoxComponent } from './modules/social-box/social-box.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { TestComponent } from './tests/test/test.component';
import { LoginComponent } from './pages/login/login.component';
import { SignInComponent } from './pages/sign-in/sign-in.component';
import { PasswordRescueComponent } from './pages/password-rescue/password-rescue.component';
import { EmailValidationComponent } from './pages/email-validation/email-validation.component';
import { WelcomeIndividualComponent } from './pages/welcome-individual/welcome-individual.component';
import { WelcomeMerchantComponent } from './pages/welcome-merchant/welcome-merchant.component';
import { WelcomeAssociationComponent } from './pages/welcome-association/welcome-association.component';
import { WelcomeAdminComponent } from './pages/welcome-admin/welcome-admin.component';







// (routing) pour faire une single-pages (plusieurs page vue en une seule, sans rechargement)
const appRoutes: Routes = 
[
  { path: 'testSpringRest', canActivate: [ConnexionGuard], component: TestSpringRestComponent },
  { path: 'appareils', canActivate: [ConnexionGuard], component: AppareilViewComponent },
  { path: 'appareils/:id', canActivate: [ConnexionGuard], component: SingleAppareilComponent },
  { path: 'connexion', component: ConnexionComponent },
  { path: '', component: AppareilViewComponent },
  { path: 'not-found', component: ErrorPage404Component },
  { path: '**', redirectTo: 'not-found' }
];



@NgModule({
  declarations: [
    AppComponent,
    AppareilComponent,
    ConnexionComponent,
    AppareilViewComponent,
    SingleAppareilComponent,
    TestSpringRestComponent,
    ErrorPage404Component,
    HeaderComponent,
    FooterComponent,
    SocialBoxComponent,
    WelcomeComponent,
    TestComponent,
    LoginComponent,
    SignInComponent,
    PasswordRescueComponent,
    EmailValidationComponent,
    WelcomeIndividualComponent,
    WelcomeMerchantComponent,
    WelcomeAssociationComponent,
    WelcomeAdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)               // les routes seront accessibles via la constante appRoutes
  ],
  providers: [AppareilService, ConnexionService, ConnexionGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
