import { Component } from '@angular/core';
import { RulesComponentComponent } from '../rules-component/rules-component.component';

@Component({
  selector: 'app-create-ruleset',
  templateUrl: './create-ruleset.component.html',
  styleUrls: ['./create-ruleset.component.css']
})
export class CreateRulesetComponent {

  public ruleset: RulesComponentComponent[] = [new RulesComponentComponent()] //dont forget to add this back

  onAddRuleClick() {
    this.ruleset.push(new RulesComponentComponent());
  }

}
