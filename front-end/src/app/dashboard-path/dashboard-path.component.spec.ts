import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardPathComponent } from './dashboard-path.component';

describe('DashboardPathComponent', () => {
  let component: DashboardPathComponent;
  let fixture: ComponentFixture<DashboardPathComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardPathComponent]
    });
    fixture = TestBed.createComponent(DashboardPathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "dashboard-path" component', () => {
    expect(component).toBeTruthy();
  });

  it('When dashboard-path is clicked mocked leavePage() function is called', () => {
    fixture.detectChanges();
    spyOn(component, 'leavePage');
    fixture.debugElement.nativeElement.querySelector('#dashboardPath').click();
    fixture.detectChanges();
    expect(component.leavePage).toHaveBeenCalled();
  });
});