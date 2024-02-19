import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppliedJobOfferComponent } from './applied-job-offer.component';

describe('AppliedJobOfferComponent', () => {
  let component: AppliedJobOfferComponent;
  let fixture: ComponentFixture<AppliedJobOfferComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AppliedJobOfferComponent]
    });
    fixture = TestBed.createComponent(AppliedJobOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
