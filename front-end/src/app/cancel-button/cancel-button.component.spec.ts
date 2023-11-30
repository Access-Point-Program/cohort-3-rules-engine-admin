import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser'
import { CancelButtonComponent } from './cancel-button.component';


describe('CancelButtonComponent', () => {
  let component: CancelButtonComponent;
  let fixture: ComponentFixture<CancelButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CancelButtonComponent]
    });
    fixture = TestBed.createComponent(CancelButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "cancel button" component', () => {
    expect(component).toBeTruthy();
  });

  it('cancel button should be rendered on the screen', () => {
    const cancelButton = fixture.debugElement.query(By.css('#cancelButton'));
    expect(cancelButton).toBeTruthy();
  });

  it('when cancel button is clicked, redirect method is called', async () => {
    fixture.detectChanges();
    spyOn(component, 'redirectToDashboard');
    fixture.debugElement.nativeElement.querySelector('#cancelButton').click();
    fixture.detectChanges();
    expect(component.redirectToDashboard).toHaveBeenCalled();
  });
});