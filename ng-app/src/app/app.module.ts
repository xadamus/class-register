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
import {EditService, GridModule, SortService, ToolbarService} from '@syncfusion/ej2-angular-grids';
import {LoginComponent} from './pages/login/login.component';
import {AuthInterceptor} from './interceptors/auth-interceptor';
import {AdminPanelComponent} from './pages/admin-panel/admin-panel.component';
import {IndexComponent} from './components/index/index.component';
import {AlertComponent} from './components/alert/alert.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UserManagementComponent} from './pages/admin-panel/pages/user-management/user-management.component';
import {ConfigurationComponent} from './pages/admin-panel/pages/configuration/configuration.component';
import {AdminDashboardComponent} from './pages/admin-panel/pages/admin-dashboard/admin-dashboard.component';

const appRoutes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'admin',
    component: AdminPanelComponent,
    children: [
      {
        path: '',
        component: AdminDashboardComponent
      },
      {
        path: 'users',
        component: UserManagementComponent
      },
      {
        path: 'configuration',
        component: ConfigurationComponent
      }
    ]
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
    AdminPanelComponent,
    IndexComponent,
    AlertComponent,
    UserManagementComponent,
    ConfigurationComponent,
    AdminDashboardComponent
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
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    EditService, ToolbarService, SortService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
