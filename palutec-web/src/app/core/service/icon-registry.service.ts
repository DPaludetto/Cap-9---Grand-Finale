import { Injectable } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root',
})
export class IconRegistryService {
  icons: IRegistryIcon[];

  loaded = false;

  constructor(private matIconRegistry: MatIconRegistry, private domSanitizer: DomSanitizer) {
    this.icons = registryIcons;
  }

  init(): void {
    if (!this.loaded) {
      this.load();
    }
  }

  private load() {
    this.icons.forEach((icon) => {
      this.matIconRegistry.addSvgIcon(
        icon.name,
        this.domSanitizer.bypassSecurityTrustResourceUrl(icon.path),
      );
    });
  }
}

export interface IRegistryIcon {
  name: string;
  path: string;
}

export const registryIcons: IRegistryIcon[] = [
  { name: 'add', path: '/assets/imgs/add.svg' },
  { name: 'menu', path: '/assets/imgs/menu.svg' },
  { name: 'dashboard', path: '/assets/imgs/dashboard.svg' },
  { name: 'app_logo', path: '/assets/imgs/app_logo.svg' },
  { name: 'app_logo_all', path: '/assets/imgs/app_logo_all.svg' },
  { name: 'edit', path: '/assets/imgs/edit.svg' },
  { name: 'attention', path: '/assets/imgs/attention.svg' },
  { name: 'clock', path: '/assets/imgs/clock.svg' },
  { name: 'register', path: '/assets/imgs/register.svg' },
  { name: 'registrations', path: '/assets/imgs/registrations.svg' },
  { name: 'calendar', path: '/assets/imgs/calendar.svg' },
  { name: 'customer', path: '/assets/imgs/customer.svg' },
  { name: 'compensation', path: '/assets/imgs/compensation.svg' },
  { name: 'configuration', path: '/assets/imgs/configuration.svg' },
  { name: 'dashboardUser', path: '/assets/imgs/dashboard_user.svg' },
  { name: 'details', path: '/assets/imgs/details.svg' },
  { name: 'error', path: '/assets/imgs/error.svg' },
  {
    name: 'consolidatedExtract',
    path: '/assets/imgs/consolidated_extract.svg',
  },
  { name: 'extract', path: '/assets/imgs/extract.svg' },
  { name: 'closure', path: '/assets/imgs/closure.svg' },
  { name: 'holiday', path: '/assets/imgs/holiday.svg' },
  { name: 'vacation', path: '/assets/imgs/vacation.svg' },
  { name: 'hoursPerCustomer', path: '/assets/imgs/hours_per_customer.svg' },
  { name: 'hoursPerProject', path: '/assets/imgs/hours_per_project.svg' },
  { name: 'launchHours', path: '/assets/imgs/launch_hours.svg' },
  { name: 'ok', path: '/assets/imgs/ok.svg' },
  { name: 'search', path: '/assets/imgs/search.svg' },
  { name: 'project', path: '/assets/imgs/project.svg' },
  { name: 'auditReport', path: '/assets/imgs/audit_report.svg' },
  { name: 'reports', path: '/assets/imgs/reports.svg' },
  { name: 'exit', path: '/assets/imgs/exit.svg' },
  { name: 'password', path: '/assets/imgs/password.svg' },
  { name: 'downArrow2', path: '/assets/imgs/down_arrow_2.svg' },
  { name: 'downArrow', path: '/assets/imgs/down_arrow.svg' },
  { name: 'upArrow2', path: '/assets/imgs/up_arrow_2.svg' },
  { name: 'upArrow', path: '/assets/imgs/up_arrow.svg' },
  { name: 'rightArrow2', path: '/assets/imgs/right_arrow_2.svg' },
  { name: 'rightArrow', path: '/assets/imgs/right_arrow.svg' },
  { name: 'leftArrow2', path: '/assets/imgs/left_arrow_2.svg' },
  { name: 'leftArrow', path: '/assets/imgs/left_arrow.svg' },
  { name: 'consolidatedUser', path: '/assets/imgs/consolidated user.svg' },
  { name: 'detailedUser', path: '/assets/imgs/detailed_user.svg' },
  { name: 'user', path: '/assets/imgs/user.svg' },
  { name: 'x', path: '/assets/imgs/x.svg' },
  { name: 'x2', path: '/assets/imgs/x2.svg' },
  { name: 'dump', path: '/assets/imgs/dump.svg' },
  { name: 'plannedHours', path: '/assets/imgs/planned_hours.svg' },
  { name: 'workedHours', path: '/assets/imgs/worked_hours.svg' },
  { name: 'calendarClock', path: '/assets/imgs/calendar_clock.svg' },
  { name: 'compensatoryTimeOff', path: '/assets/imgs/compensatory_time_off.svg' },
  { name: 'check', path: '/assets/imgs/check.svg' },
  { name: 'trash', path: '/assets/imgs/trash.svg' },
  { name: 'logout', path: '/assets/imgs/logout.svg' },
  { name: 'circleX', path: '/assets/imgs/circle_x.svg' },
  { name: 'exit_1', path: '/assets/imgs/exit_1.svg' },
  { name: 'entry', path: '/assets/imgs/entry_1.svg' },
  { name: 'in_out', path: '/assets/imgs/in_out.svg' },
  { name: 'monthlyClosing', path: '/assets/imgs/monthly_closing.svg' },
  { name: 'project_performance', path: '/assets/imgs/project-performance.svg' },
  { name: 'adjustment', path: '/assets/imgs/adjustment.svg' },
  { name: 'administration', path: '/assets/imgs/cogs-solid.svg' },
  { name: 'demand', path: '/assets/imgs/archive-solid.svg' },
  { name: 'copy_clb', path: '/assets/imgs/copy_clb.svg' },
];