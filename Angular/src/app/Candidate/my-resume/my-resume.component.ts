
import { Component, OnInit } from '@angular/core';
import { ResumeService } from 'src/app/services/resume.service';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-my-resume',
  templateUrl: './my-resume.component.html',
  styleUrls: ['./my-resume.component.scss']
})
export class MyResumeComponent implements OnInit {
  pdfUrl: SafeResourceUrl | null =null;

  constructor(private resumeService: ResumeService,private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.loadResume();
  }

  loadResume() {
    this.resumeService.downloadResume().subscribe(
      (response: Blob) => {
        const file = new Blob([response], { type: 'application/pdf' });
        const url = URL.createObjectURL(file);
        this.pdfUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
      },
      error => {
        console.error('Error downloading resume:', error);
      }
    );
  }
}
