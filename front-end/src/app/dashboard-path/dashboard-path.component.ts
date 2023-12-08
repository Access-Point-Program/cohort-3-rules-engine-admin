import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard-path',
  templateUrl: './dashboard-path.component.html',
  styleUrls: ['./dashboard-path.component.css']
})
export class DashboardPathComponent {
  leavePage(url: string){
    if (confirm("Are you sure you want to leave this page?\nAll unsaved changes will be lost.")) {
      window.location.href = url;  
    }
  }
}