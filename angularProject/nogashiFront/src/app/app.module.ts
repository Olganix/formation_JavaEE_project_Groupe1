import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

// services
import { ConnexionService } from './services/connexion.service';
import { ConnexionGuard } from './services/connexion-guard.service';
import { InfoBoxNotificationsService } from './services/InfoBoxNotifications.services';

import { AppareilService } from './services/appareil.service';


// Root
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/root/app.component';

// Common/Modules
import { HeaderComponent } from './components/common/header/header.component';
import { FooterComponent } from './components/common/footer/footer.component';
import { SocialBoxComponent } from './components/modules/social-box/social-box.component';

// pages
import { WelcomeComponent } from './components/pages/pages_anonyme/welcome/welcome.component';
import { SignInComponent } from './components/pages/pages_anonyme/sign-in/sign-in.component';
import { EmailValidationComponent } from './components/pages/pages_anonyme/email-validation/email-validation.component';
import { LoginComponent } from './components/pages/pages_anonyme/login/login.component';
import { PasswordRescueComponent } from './components/pages/pages_anonyme/password-rescue/password-rescue.component';
import { ErrorPage404Component } from './components/pages/pages_anonyme/error-page404/error-page404.component';



// pages with connexion
import { WelcomeIndividualComponent } from './components/pages/pages_individual/welcome-individual/welcome-individual.component';
import { WelcomeMerchantComponent } from './components/pages/pages_merchant/welcome-merchant/welcome-merchant.component';
import { WelcomeAssociationComponent } from './components/pages/pages_association/welcome-association/welcome-association.component';
import { WelcomeAdminComponent } from './components/pages/pages_admin/welcome-admin/welcome-admin.component';

// tests
import { TestComponent } from './components/tests/test/test.component';
import { ConnexionComponent } from './components/tests/connexion/connexion.component';
import { AppareilViewComponent } from './components/tests/appareil-view/appareil-view.component';
import { AppareilComponent } from './components/tests/appareil/appareil.component';
import { SingleAppareilComponent } from './components/tests/single-appareil/single-appareil.component';
import { TestSpringRestComponent } from './components/tests/test-spring-rest/test-spring-rest.component';
import { InfoBoxNotificationsComponent } from './components/common/info-box-notifications/info-box-notifications.component';







// (routing) pour faire une single-pages (plusieurs page vue en une seule, sans rechargement)
const appRoutes: Routes =
[
  { path: 'welcome', component: WelcomeComponent },
  { path: 'signin', component: SignInComponent },
  { path: 'emailValidation', component: EmailValidationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'passwordRescue', component: PasswordRescueComponent },

  { path: 'individual/welcome', canActivate: [ConnexionGuard], component: WelcomeIndividualComponent },
  { path: 'merchant/welcome', canActivate: [ConnexionGuard], component: WelcomeMerchantComponent },
  { path: 'association/welcome', canActivate: [ConnexionGuard], component: WelcomeAssociationComponent },
  { path: 'admin/welcome', canActivate: [ConnexionGuard], component: WelcomeAdminComponent },

  { path: 'test', component: TestComponent },
  { path: 'test/testSpringRest', canActivate: [ConnexionGuard], component: TestSpringRestComponent },
  { path: 'test/appareils', canActivate: [ConnexionGuard], component: AppareilViewComponent },
  { path: 'test/appareils/:id', canActivate: [ConnexionGuard], component: SingleAppareilComponent },
  { path: 'test/connexion', component: ConnexionComponent },

  { path: '', component: WelcomeComponent },
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
    WelcomeAdminComponent,
    InfoBoxNotificationsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    ReactiveFormsModule,
    // les routes seront accessibles via la constante appRoutes
  ],
  providers: [ConnexionService, AppareilService, ConnexionGuard, InfoBoxNotificationsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
