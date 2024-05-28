import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaysFormComponent } from './pays-form.component';

describe('PaysFormComponent', () => {
  let component: PaysFormComponent;
  let fixture: ComponentFixture<PaysFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaysFormComponent]
    });
    fixture = TestBed.createComponent(PaysFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
