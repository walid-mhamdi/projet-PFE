import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaysListComponent } from './pays-list.component';

describe('PaysListComponent', () => {
  let component: PaysListComponent;
  let fixture: ComponentFixture<PaysListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaysListComponent]
    });
    fixture = TestBed.createComponent(PaysListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
