import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BanqueListComponent } from './banque-list.component';

describe('BanqueListComponent', () => {
  let component: BanqueListComponent;
  let fixture: ComponentFixture<BanqueListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BanqueListComponent]
    });
    fixture = TestBed.createComponent(BanqueListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
