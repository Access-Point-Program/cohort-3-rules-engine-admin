import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SidebarComponent } from './sidebar.component';
import { By } from '@angular/platform-browser';

describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarComponent]
    });
    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "sidebar" component', () => {
    expect(component).toBeTruthy();
  });

  it('sidebar should be rendered', () => {
    fixture.detectChanges();
    const rendered = fixture.debugElement.nativeElement.querySelector('#sidebar-title');
    expect(rendered.innerHTML).toBe('Super Cool Name');
  });

  describe('Should render all sidebar text', () => {
    it('should render "Dashboard"', () => {
      const dashboard = fixture.debugElement.query(By.css('#dashboard')).nativeElement;
      expect(dashboard.innerHTML).toBe('Dashboard');
    });
    it('should render "Reports"', () => {
      const reports = fixture.debugElement.query(By.css('#reports')).nativeElement;
      expect(reports.innerHTML).toBe('Reports');
  
    });
    it('should render "Simulation"', () => {
      const simulation = fixture.debugElement.query(By.css('#simulation')).nativeElement;
      expect(simulation.innerHTML).toBe('Simulation');
  
    });
    it('should render "Sign Out"', () => {
      const signOut = fixture.debugElement.query(By.css('#signOut')).nativeElement;
      expect(signOut.innerHTML).toBe('Sign Out');
    });
  });
});