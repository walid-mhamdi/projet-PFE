import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NationaliteListComponent } from './nationalite-list.component';

describe('NationaliteListComponent', () => {
  let component: NationaliteListComponent;
  let fixture: ComponentFixture<NationaliteListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NationaliteListComponent]
    });
    fixture = TestBed.createComponent(NationaliteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
