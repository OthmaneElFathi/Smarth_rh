import { Component } from '@angular/core';
import { ResumeService } from 'src/app/services/resume.service';
import { ResumeData } from 'src/app/types/resume.interface';

@Component({
  selector: 'app-upload-resume',
  templateUrl: './upload-resume.component.html',
  styleUrls: ['./upload-resume.component.scss']
})
export class UploadResumeComponent {
  resumeData: ResumeData = {
    title: '',
    resume: "",
  };
  uploading = false;

  constructor(private resumeService: ResumeService) { }

  onSubmit() {
    this.uploading = true;
    this.resumeService.uploadResume(this.resumeData).subscribe(
      response => {
        console.log('Resume uploaded successfully:', response);
        localStorage.setItem('hasResume',JSON.stringify(true));
        this.uploading = false;
      },
      error => {
        console.error('Error uploading resume:', error);
        
        this.uploading = false;
      }
    );
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    this.resumeData.resume = file;
    const fileNameElement = document.getElementById('fileName');
      if (fileNameElement) {
        fileNameElement.textContent = file?.name || 'No file selected';
      }
    }
  }

