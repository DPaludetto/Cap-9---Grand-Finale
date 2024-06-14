import { Component, Input } from '@angular/core';
import { PrimeIcons } from 'primeng/api';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss']
})
export class TimelineComponent {

  @Input() events: any[] = [];

  templateType = 'marker';

  ngOnInit() {
      this.templateType = (this.events.every(it =>  typeof it === 'string') ? 'content' : 'marker');
  }
}
