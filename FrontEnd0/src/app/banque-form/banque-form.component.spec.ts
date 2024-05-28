import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BanqueFormComponent } from './banque-form.component';

describe('BanqueFormComponent', () => {
  let component: BanqueFormComponent;
  let fixture: ComponentFixture<BanqueFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BanqueFormComponent]
    });
    fixture = TestBed.createComponent(BanqueFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
