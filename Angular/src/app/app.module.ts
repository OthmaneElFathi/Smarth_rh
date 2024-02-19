import { MatMenuModule } from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon'
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './Auth/register/register.component';
import { LoginComponent } from './Auth/login/login.component';
import { AuthInterceptor } from './Auth/auth.interceptor';
import { FormsModule } from '@angular/forms';
import { UploadResumeComponent } from './Candidate/upload-resume/upload-resume.component';
import { CreateJobOfferComponent } from './Recruiter/create-job-offer/create-job-offer.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { JobOffersComponent } from './Candidate/job-offers/job-offers.component';
import { JobOfferComponent } from './Candidate/job-offer/job-offer.component';
import { MyJobOffersComponent } from './Recruiter/my-job-offers/my-job-offers.component';
import { MatDialogModule } from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditJobOfferComponent } from './Recruiter/edit-job-offer/edit-job-offer.component';
import { MyJobOfferComponent } from './Recruiter/my-job-offer/my-job-offer.component';
import { ApplicationsComponent } from './Candidate/applications/applications.component';
import { AppliedJobOfferComponent } from './Candidate/applied-job-offer/applied-job-offer.component';
import { MyResumeComponent } from './Candidate/my-resume/my-resume.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    UploadResumeComponent,
    CreateJobOfferComponent,
    NavbarComponent,
    FooterComponent,
    JobOffersComponent,
    JobOfferComponent,
    MyJobOffersComponent,
    EditJobOfferComponent,
    MyJobOfferComponent,
    ApplicationsComponent,
    AppliedJobOfferComponent,
    MyResumeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatDialogModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
