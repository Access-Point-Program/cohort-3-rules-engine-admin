import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient, HttpResponse } from '@angular/common/http';
import { SaveButtonComponent } from './save-button.component';
import { By } from '@angular/platform-browser';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { HttpClientTestingModule, HttpTestingController  } from '@angular/common/http/testing'; 
import { of } from 'rxjs';



describe('SaveButtonComponent', () => {
  let component: SaveButtonComponent;
  let fixture: ComponentFixture<SaveButtonComponent>;
  let httpClient: HttpClient;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaveButtonComponent],
      imports: [HttpClientModule, 
        HttpClientTestingModule],
      providers: [CreateRulesetComponent],
    });

    httpClient = TestBed.inject(HttpClient);

    fixture = TestBed.createComponent(SaveButtonComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify(); //Verifies that no requests are outstanding.
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });

  it('should render a save button on the screen', () => {
    const saveButton = fixture.debugElement.query(By.css('#saveButton'));
    expect(saveButton).toBeTruthy();
  });

  it('should make a POST request when saveData is called', () => {
    
    
  });
});
