import { Component } from '@angular/core';
import { RulesComponentComponent } from './rules-component/rules-component.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front-end';

  public ruleset: RulesComponentComponent[] = [new RulesComponentComponent(), new RulesComponentComponent()]
}
