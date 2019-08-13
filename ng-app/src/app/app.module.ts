import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NavigationComponent} from './components/navigation/navigation.component';
import {ContactComponent} from './pages/contact/contact.component';
import {MarksComponent} from './pages/marks/marks.component';
import {NotFoundComponent} from './pages/not-found/not-found.component';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MarksGridComponent} from './pages/marks/marks-grid/marks-grid.component';
import {GridModule} from '@syncfusion/ej2-angular-grids';
import {LoginComponent} from './pages/login/login.component';
import {AuthInterceptor} from './interceptors/auth-interceptor';
import {AdminDashboardComponent} from './pages/admin-dashboard/admin-dashboard.component';
import {IndexComponent} from './components/index/index.component';
import {AlertComponent} from './components/alert/alert.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

const appRoutes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'admin',
    component: AdminDashboardComponent
  },
  {
    path: 'marks',
    component: MarksComponent
  },
  {
    path: 'contact',
    component: ContactComponent
  },
  {
    path: '',
    component: IndexComponent,
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    ContactComponent,
    MarksComponent,
    NotFoundComponent,
    MarksGridComponent,
    LoginComponent,
    AdminDashboardComponent,
    IndexComponent,
    AlertComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes, {enableTracing: true}),
    FormsModule,
    HttpClientModule,
    GridModule,
    BrowserAnimationsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
