
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JobOfferResponse } from '../../types/job-offer.interface';
import { JobOfferService } from '../../services/job-offer.service';
import { PaginatedCandidateResponse } from '../../types/candidate.interface';
import { CandidateService } from '../../services/candidate.service';
import { ResumeService } from '../../services/resume.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Observable, catchError, map, throwError } from 'rxjs';

@Component({
  selector: 'app-my-job-offer',
  templateUrl: './my-job-offer.component.html',
  styleUrls: ['./my-job-offer.component.scss']
})
export class MyJobOfferComponent implements OnInit {
  jobOffer: JobOfferResponse | undefined;
  candidates : PaginatedCandidateResponse | null = null;
  currentPage = 0;
  pageSize = 4;
  searchQuery: string = ''; 

  constructor(private sanitizer: DomSanitizer,private route: ActivatedRoute, private resumeService: ResumeService,private router: Router,private jobOfferService: JobOfferService,private candidateService: CandidateService) { }

  ngOnInit(): void {
    this.getJobOfferDetails();
    this.getApplicants();
  }
  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getApplicants();
    }
  }
  
  nextPage() {
    if (this.candidates && !this.candidates.last) { 
      this.currentPage++;
      this.getApplicants();
    }
  }

  getJobOfferDetails(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.jobOfferService.getJobOfferById(id).subscribe(
        jobOffer => {
          this.jobOffer = jobOffer;
        },
        error => {
          console.error('Error fetching job offer details:', error);
        }
      );
    }
  }
  getApplicants(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.candidateService.fetchApplicants(id).subscribe(
        candidates => {
          this.candidates = candidates;
          candidates.content.forEach((candidate) => {
            this.loadPicture(candidate.username).subscribe(
              (url: string) => {
                candidate.profilePictureUrl = url;
                console.log(url);
              },
              (error) => {
                console.error('Error fetching profile picture for candidate:', error);
              }
            );
          });
        },
        error => {
          console.error('Error fetching job offer details:', error);
        }
      );
    }
  }
  
  loadPicture(username: string): Observable<string> {
    return this.candidateService.downloadProfilePicture(username).pipe(
      map((response: Blob) => {
        const file = new Blob([response], { type: 'image/jpeg' });
        return URL.createObjectURL(file);
      }),
      catchError((error: any) => {
        console.error('Error downloading profile picture:', error);
        return throwError(error);
      })
    );
  }
  
  loadResume(username:string) {
    this.resumeService.downloadResumeCandidate(username).subscribe(
      (response: Blob) => {
        const file = new Blob([response], { type: 'application/pdf' });
        const url = URL.createObjectURL(file);
        window.open(url,"_blank");
      },
      error => {
        console.error('Error downloading resume:', error);
      }
    );
  }
  editJobOffer() {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate(['/edit-job-offer', id]);
  }

  deleteJobOffer() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.jobOfferService.deleteJobOffer(id).subscribe(
        () => {
          this.router.navigate(['/my-job-offers']);
        },
        error => {
          console.error('Error deleting job offer:', error);
        }
      );
    }
  }
}
