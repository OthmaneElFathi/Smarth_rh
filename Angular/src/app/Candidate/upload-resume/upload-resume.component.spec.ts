import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadResumeComponent } from './upload-resume.component';

describe('UploadResumeComponent', () => {
  let component: UploadResumeComponent;
  let fixture: ComponentFixture<UploadResumeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UploadResumeComponent]
    });
    fixture = TestBed.createComponent(UploadResumeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
