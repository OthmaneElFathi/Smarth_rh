import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Auth/login/login.component';
import { RegisterComponent } from './Auth/register/register.component';
import { UploadResumeComponent } from './Candidate/upload-resume/upload-resume.component';
import { CreateJobOfferComponent } from './Recruiter/create-job-offer/create-job-offer.component'; 
import { JobOffersComponent } from './Candidate/job-offers/job-offers.component';
import { JobOfferComponent } from './Candidate/job-offer/job-offer.component';
import { MyJobOffersComponent } from './Recruiter/my-job-offers/my-job-offers.component';
import { EditJobOfferComponent } from './Recruiter/edit-job-offer/edit-job-offer.component';
import { MyJobOfferComponent } from './Recruiter/my-job-offer/my-job-offer.component';
import { ApplicationsComponent } from './Candidate/applications/applications.component';
import { AppliedJobOfferComponent } from './Candidate/applied-job-offer/applied-job-offer.component';
import { MyResumeComponent } from './Candidate/my-resume/my-resume.component';
const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'upload-resume', component: UploadResumeComponent },
  { path: 'create-job-offer', component: CreateJobOfferComponent },
  { path: 'edit-job-offer/:id', component: EditJobOfferComponent },
  { path: 'my-job-offers', component: MyJobOffersComponent },
  { path: 'job-offers', component: JobOffersComponent },
  { path: 'applied-job-offer/:id', component: AppliedJobOfferComponent },
  { path: 'job-offer/:id', component: JobOfferComponent },
  { path: 'my-applications', component: ApplicationsComponent },
  { path: 'my-job-offer/:id', component: MyJobOfferComponent },
  { path: 'my-resume', component: MyResumeComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, 
  { path: '**', redirectTo: '/login' } 
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
