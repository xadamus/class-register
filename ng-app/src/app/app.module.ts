import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NavigationComponent} from './navigation/navigation.component';
import {ContactComponent} from './contact/contact.component';
import {MarksComponent} from './marks/marks.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {MarksGridComponent} from './marks/marks-grid/marks-grid.component';
import {GridModule} from '@syncfusion/ej2-angular-grids';

const appRoutes = [
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
    component: MarksComponent,
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
    MarksGridComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes, { enableTracing: true }),
    FormsModule,
    HttpClientModule,
    GridModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
