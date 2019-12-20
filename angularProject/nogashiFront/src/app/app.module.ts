import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';


import { EditorModule } from '@tinymce/tinymce-angular';

// Services
import { ConnexionService } from './services/connexion.service';
import { ConnexionGuard } from './services/connexion-guard.service';
import { InfoBoxNotificationsService } from './services/InfoBoxNotifications.services';

import { AppareilService } from './services/appareil.service';


// Guards
import {ConnexionMerchantGuard} from './services/guards/connexion-merchant.guard';
import {ConnexionIndividualGuard} from './services/guards/connexion-individual.guard';
import {ConnexionAdminGuard} from './services/guards/connexion-admin.guard';
import {ConnexionAssociationGuard} from './services/guards/connexion-association.guard';



// Root
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/root/app.component';

// Common/Modules
import { HeaderComponent } from './components/common/header/header.component';
import { FooterComponent } from './components/common/footer/footer.component';
import { SocialBoxComponent } from './components/modules/social-box/social-box.component';

// Pages
import { WelcomeComponent } from './components/pages/pages_anonyme/welcome/welcome.component';
import { SignInComponent } from './components/pages/pages_anonyme/sign-in/sign-in.component';
import { EmailValidationComponent } from './components/pages/pages_anonyme/email-validation/email-validation.component';
import { LoginComponent } from './components/pages/pages_anonyme/login/login.component';
import { PasswordRescueComponent } from './components/pages/pages_anonyme/password-rescue/password-rescue.component';
import { ErrorPage404Component } from './components/pages/pages_anonyme/error-page404/error-page404.component';




// Pages with connexion
import { WelcomeIndividualComponent } from './components/pages/pages_individual/welcome-individual/welcome-individual.component';
import { WelcomeMerchantComponent } from './components/pages/pages_merchant/welcome-merchant/welcome-merchant.component';
import { WelcomeAssociationComponent } from './components/pages/pages_association/welcome-association/welcome-association.component';
import { WelcomeAdminComponent } from './components/pages/pages_admin/welcome-admin/welcome-admin.component';


// Tests
import { TestComponent } from './components/tests/test/test.component';
import { ConnexionComponent } from './components/tests/connexion/connexion.component';
import { AppareilViewComponent } from './components/tests/appareil-view/appareil-view.component';
import { AppareilComponent } from './components/tests/appareil/appareil.component';
import { SingleAppareilComponent } from './components/tests/single-appareil/single-appareil.component';
import { TestSpringRestComponent } from './components/tests/test-spring-rest/test-spring-rest.component';
import { InfoBoxNotificationsComponent } from './components/common/info-box-notifications/info-box-notifications.component';
import { GeneralFaqComponent } from './components/pages/pages_anonyme/general-faq/general-faq.component';
import { FaqMerchantComponent } from './components/pages/pages_merchant/faq-merchant/faq-merchant.component';
import { LegalNoticesComponent } from './components/pages/pages_anonyme/legal-notices/legal-notices.component';
import { FindUsComponent } from './components/pages/pages_anonyme/find-us/find-us.component';
import { PasswordResetComponent } from './components/pages/pages_common/password-reset/password-reset.component';
import { ShoppingCartComponent } from './components/pages/pages_common/shopping-cart/shopping-cart.component';
import { CommandStepsComponent } from './components/pages/pages_common/command-steps/command-steps.component';
import { CommandRecapComponent } from './components/pages/pages_common/command-recap/command-recap.component';
import { CommandPaymentComponent } from './components/pages/pages_common/command-payment/command-payment.component';
import { CommandConfirmationComponent } from './components/pages/pages_common/command-confirmation/command-confirmation.component';
import { TchatComponent } from './components/pages/pages_common/tchat/tchat.component';
import { ProductSheetComponent } from './components/pages/pages_common/product-sheet/product-sheet.component';
import { CommentsComponent } from './components/modules/comments/comments.component';
import { DeactivateAccountComponent } from './components/pages/pages_common/deactivate-account/deactivate-account.component';
import { PasswordRescueModificationComponent } from './components/pages/pages_anonyme/password-rescue-modification/password-rescue-modification.component';
import { ListProductsTemplatesComponent } from './components/pages/pages_merchant/list-products-templates/list-products-templates.component';
import { CompteComponent } from './components/pages/pages_common/compte/compte.component';
import { CompteCommandesComponent } from './components/pages/pages_common/compte-commandes/compte-commandes.component';







// (routing) pour faire une single-pages (plusieurs page vue en une seule, sans rechargement)
const appRoutes: Routes =
[
  { path: 'welcome', component: WelcomeComponent },
  { path: 'signin', component: SignInComponent },
  { path: 'emailValidation', component: EmailValidationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'passwordRescue', component: PasswordRescueComponent },
  { path: 'passwordRescueModification', component: PasswordRescueModificationComponent },
  { path: 'generalFaq', component: GeneralFaqComponent },
  { path: 'legalNotices', component: LegalNoticesComponent },
  { path: 'findUs', component: FindUsComponent },


  { path: 'passwordReset', component: PasswordResetComponent },
  { path: 'deactivateAccount', component: DeactivateAccountComponent },
  { path: 'commandSteps', component: CommandStepsComponent },
  { path: 'shoppingCart', component: ShoppingCartComponent },
  { path: 'commandRecap', component: CommandRecapComponent },
  { path: 'commandPayment', component: CommandPaymentComponent },
  { path: 'commandConfirmation', component: CommandConfirmationComponent },
  { path: 'tchat', component: TchatComponent },
  { path: 'comments', component: CommentsComponent },
  { path: 'productSheet', component: ProductSheetComponent },
  { path: 'myProfile', component: CompteComponent },
  { path: 'myOrders', component: CompteCommandesComponent },
  { path: 'compte', component: CompteComponent },
  { path: 'compteCommandes', component: CompteCommandesComponent },

  { path: 'individual/welcome',   canActivate: [ConnexionIndividualGuard],  component: WelcomeIndividualComponent },


  { path: 'merchant/welcome',     canActivate: [ConnexionMerchantGuard],    component: WelcomeMerchantComponent },
  { path: 'merchant/faq',         canActivate: [ConnexionMerchantGuard],    component: FaqMerchantComponent },
  { path: 'merchant/listProductsTemplates', canActivate: [ConnexionGuard], component: ListProductsTemplatesComponent },


  { path: 'association/welcome',  canActivate: [ConnexionAssociationGuard], component: WelcomeAssociationComponent },


  { path: 'admin/welcome',        canActivate: [ConnexionAdminGuard],       component: WelcomeAdminComponent },


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
    InfoBoxNotificationsComponent,
    GeneralFaqComponent,
    FaqMerchantComponent,
    LegalNoticesComponent,
    FindUsComponent,
    PasswordResetComponent,
    ShoppingCartComponent,
    CommandStepsComponent,
    CommandRecapComponent,
    CommandPaymentComponent,
    CommandConfirmationComponent,
    TchatComponent,
    ProductSheetComponent,
    CommentsComponent,
    DeactivateAccountComponent,
    CompteComponent,
    CompteCommandesComponent,
    PasswordRescueModificationComponent,
    ListProductsTemplatesComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),              // les routes seront accessibles via la constante appRoutes
    ReactiveFormsModule,
    EditorModule
  ],
  providers: [ConnexionService, AppareilService, ConnexionGuard, InfoBoxNotificationsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
