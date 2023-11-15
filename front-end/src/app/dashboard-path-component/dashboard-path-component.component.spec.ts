import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardPathComponentComponent } from './dashboard-path-component.component';

describe('DashboardPathComponentComponent', () => {
  let component: DashboardPathComponentComponent;
  let fixture: ComponentFixture<DashboardPathComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardPathComponentComponent]
    });
    fixture = TestBed.createComponent(DashboardPathComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
