import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NavigationComponent} from './components/navigation/navigation.component';
import {NotFoundComponent} from './pages/not-found/not-found.component';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CommandColumnService, EditService, GridModule, SortService, ToolbarService} from '@syncfusion/ej2-angular-grids';
import {LoginComponent} from './pages/login/login.component';
import {AuthInterceptor} from './interceptors/auth-interceptor';
import {AdminPanelComponent} from './pages/admin-panel/admin-panel.component';
import {IndexComponent} from './components/index/index.component';
import {AlertComponent} from './components/alert/alert.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UserManagementComponent} from './pages/admin-panel/pages/user-management/user-management.component';
import {ConfigurationComponent} from './pages/admin-panel/pages/configuration/configuration.component';
import {AdminDashboardComponent} from './pages/admin-panel/pages/admin-dashboard/admin-dashboard.component';
import {ModalModule} from 'ngx-bootstrap/modal';
import {StudentManagementComponent} from './pages/admin-panel/pages/student-management/student-management.component';
import {TeacherManagementComponent} from './pages/admin-panel/pages/teacher-management/teacher-management.component';
import {SemesterComponent} from './pages/admin-panel/pages/semester/semester.component';
import {SubjectsComponent} from './pages/admin-panel/pages/subjects/subjects.component';
import {ProfilesComponent} from './pages/admin-panel/pages/profiles/profiles.component';
import {TeacherPanelComponent} from './pages/teacher-panel/teacher-panel.component';
import {TeacherDashboardComponent} from './pages/teacher-panel/pages/teacher-dashboard/teacher-dashboard.component';
import {TeacherMarksComponent} from './pages/teacher-panel/pages/teacher-marks/teacher-marks.component';
import {StudentPanelComponent} from './pages/student-panel/student-panel.component';
import {StudentDashboardComponent} from './pages/student-panel/pages/student-dashboard/student-dashboard.component';
import {StudentMarksComponent} from './pages/student-panel/pages/student-marks/student-marks.component';
import {ParentPanelComponent} from './pages/parent-panel/parent-panel.component';
import {ParentDashboardComponent} from './pages/parent-panel/pages/parent-dashboard/parent-dashboard.component';
import {ParentMarksComponent} from './pages/parent-panel/pages/parent-marks/parent-marks.component';

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
        path: 'students',
        component: StudentManagementComponent
      },
      {
        path: 'teachers',
        component: TeacherManagementComponent
      },
      {
        path: 'semesters',
        component: SemesterComponent
      },
      {
        path: 'subjects',
        component: SubjectsComponent
      },
      {
        path: 'profiles',
        component: ProfilesComponent
      }
    ]
  },
  {
    path: 'teacher',
    component: TeacherPanelComponent,
    children: [
      {
        path: '',
        component: TeacherDashboardComponent
      },
      {
        path: 'marks',
        component: TeacherMarksComponent
      }
    ]
  },
  {
    path: 'student',
    component: StudentPanelComponent,
    children: [
      {
        path: '',
        component: StudentDashboardComponent
      },
      {
        path: 'marks',
        component: StudentMarksComponent
      }
    ]
  },
  {
    path: 'parent',
    component: ParentPanelComponent,
    children: [
      {
        path: '',
        component: ParentDashboardComponent
      },
      {
        path: 'marks',
        component: ParentMarksComponent
      }
    ]
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
    NotFoundComponent,
    LoginComponent,
    AdminPanelComponent,
    IndexComponent,
    AlertComponent,
    UserManagementComponent,
    ConfigurationComponent,
    AdminDashboardComponent,
    StudentManagementComponent,
    TeacherManagementComponent,
    SemesterComponent,
    SubjectsComponent,
    ProfilesComponent,
    TeacherPanelComponent,
    TeacherDashboardComponent,
    TeacherMarksComponent,
    StudentPanelComponent,
    StudentDashboardComponent,
    StudentMarksComponent,
    ParentPanelComponent,
    ParentDashboardComponent,
    ParentMarksComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes, {enableTracing: true}),
    FormsModule,
    HttpClientModule,
    GridModule,
    BrowserAnimationsModule,
    ModalModule.forRoot()
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    EditService, ToolbarService, SortService, CommandColumnService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
