import { Component } from '@angular/core';

@Component({
  selector: 'app-cancel-button',
  templateUrl: './cancel-button.component.html',
  styleUrls: ['./cancel-button.component.css']
})
export class CancelButtonComponent {

  redirectToDashboard(){
    window.location.href = "http://host.docker.internal:9030/rulesets";
  }
}
