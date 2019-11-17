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

import { ConnexionComponent } from './connexion/connexion.component';
import { AppareilViewComponent } from './appareil-view/appareil-view.component';
import { AppareilComponent } from './appareil/appareil.component';
import { SingleAppareilComponent } from './single-appareil/single-appareil.component';
import { TestSpringRestComponent } from './test-spring-rest/test-spring-rest.component';
import { ErrorPage404Component } from './error-page404/error-page404.component';







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
    ErrorPage404Component
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
