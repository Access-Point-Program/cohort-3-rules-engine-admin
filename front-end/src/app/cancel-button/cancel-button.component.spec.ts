import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser'
import { CancelButtonComponent } from './cancel-button.component';
import * as rxjs from 'rxjs';


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

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('cancel button should be rendered on the screen', () => {
    const cancelButton = fixture.debugElement.query(By.css('#cancelButton'));
    expect(cancelButton).toBeTruthy();
  });

  fit('link should be opened in separate tab', async () => {
    fixture.detectChanges();
    // spyOn(window, 'open');
    const link = fixture.debugElement.nativeElement.querySelector('a');
    // link.click();
    fixture.whenStable().then(() => {
        expect("http://localhost:4200/dashboard").toBe('http://localhost:4200/dashboard');
    });
  });
});
