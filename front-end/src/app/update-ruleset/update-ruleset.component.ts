import { AfterViewChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs';
import { ConditionsComponent } from '../conditions/conditions.component';

@Component({
  selector: 'app-update-ruleset',
  templateUrl: './update-ruleset.component.html',
  styleUrls: ['./update-ruleset.component.css']
})
export class UpdateRulesetComponent implements OnInit, AfterViewInit, AfterViewChecked {
  @ViewChildren(RulesComponentComponent) viewChildren!: QueryList<RulesComponentComponent>;
  public ruleset: RulesComponentComponent[] = [new RulesComponentComponent()]
  public rulesetDatabaseId?: number;
  public name:string = '';
  private url: string = 'http://localhost:9004/ruleset/';
  public paramsSubscription$!: Subscription;
  public id!: string | null;

  constructor(private changeDetectorRef: ChangeDetectorRef, private route: ActivatedRoute){}

  ngOnInit() {
    this.paramsSubscription$ = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = params.get("id");
        fetch(this.url + this.id)
        .then((response) => {
          if(!response.ok) throw new Error("There was an issue retrieving the ruleset. Fetch response status code was not successful.");
          return response.json();
        })
        .then((response) => {
          const recievedRuleset: RulesComponentComponent[] = [];
          for(let incomingRuleIndex: number = 0; incomingRuleIndex < response.rules.length; incomingRuleIndex++) {
            const recievedConditions: ConditionsComponent[] = [];
            const currentRule = response.rules[incomingRuleIndex];
            const newRule: RulesComponentComponent = new RulesComponentComponent;
            for(let incConditionIndex: number = 0; incConditionIndex < currentRule.conditions.length; incConditionIndex++){
              const currentCondition = currentRule.conditions[incConditionIndex];
              const newCondition: ConditionsComponent = new ConditionsComponent;
              newCondition.conditionWhenValue = currentCondition.fact_type;
              newCondition.conditionIsValue = currentCondition.value_type;
              newCondition.conditionDatabaseId = currentCondition.id;
              recievedConditions.push(newCondition);
            }
            newRule.childrenConditions = recievedConditions;
            newRule.thenValue = currentRule.event_type;
            newRule.priority = currentRule.priority;
            newRule.ruleDatabaseId = currentRule.id;
            recievedRuleset.push(newRule);
          }
          this.ruleset = recievedRuleset;
          this.ruleset.map(rule => rule.setPriority(-1));
          this.name = response.name;
          this.rulesetDatabaseId = response.id;
        }).catch((e) => {
          window.alert("There was an issue populating the webpage.\n\nError: " + e);
          window.location.href = "https://google.com/about";
        })
      }
    );
  }

  ngOnDestroy() {
    if (this.paramsSubscription$) this.paramsSubscription$.unsubscribe();
  }

  ngAfterViewInit(): void {
    this.ruleset = this.viewChildren.toArray();
    this.changeDetectorRef.detectChanges();
  }

  ngAfterViewChecked() {
    for(let i = 0; i < this.ruleset.length; i++){
      if(this.ruleset[i].priority == undefined || this.ruleset[i].priority == -1 ){
        this.ruleset[i].priority = this.viewChildren.toArray()[i].priority;
      }
    }
  }

  forceUpdateRuleset() {
    this.ruleset = this.viewChildren.toArray();
    this.ruleset.forEach(rule => {
      rule.childrenConditions = rule.viewChildren.toArray();
    })
  }

  onAddRuleClick() {
    this.ruleset.push(new RulesComponentComponent());
  }

  priorityMoveUp() {
    this.forceUpdateRuleset();
  }

  priorityMoveDown() {
    this.forceUpdateRuleset();
  }

  updateSaveButtonClick():RulesComponentComponent[]{
    this.forceUpdateRuleset();
    return this.ruleset;
  }

  getName():string{
    return this.name;
  }

  public updateRuleset() {
    this.ruleset.sort(function(a, b) {
      const priorityA = a.priority;
      const priorityB = b.priority;
      return (priorityA < priorityB) ? -1 : (priorityA > priorityB) ? 1 : 0;
    });
  }
}
