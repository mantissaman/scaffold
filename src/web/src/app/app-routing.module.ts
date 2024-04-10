import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { AddGenericComponent } from './components/generic/add-generic/add-generic.component';
import { GenericListComponent } from './components/generic/generic-list/generic-list.component';
import { GenericDetailsComponent } from './components/generic/generic-details/generic-details.component';
const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'add-generic', component: AddGenericComponent },
  { path: 'generics', component: GenericListComponent },
  { path: 'generics/:id', component: GenericDetailsComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }