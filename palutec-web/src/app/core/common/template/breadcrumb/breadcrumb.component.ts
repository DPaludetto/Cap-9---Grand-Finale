import { IBreadCrumb } from './bread-crumb';
import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute, Event, NavigationEnd, Router,
} from '@angular/router';
import { filter, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
})
export class BreadcrumbComponent implements OnInit {
  static readonly ROUTE_DATA_BREADCRUMB = 'breadcrumb';

  breadcrumbs: IBreadCrumb[] = [];

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.router.events.pipe(
      filter((event: Event) => event instanceof NavigationEnd),
      distinctUntilChanged(),
    ).subscribe(() => {
      this.breadcrumbs = this.buildBreadCrumb(this.activatedRoute.root);
    });
  }

  private buildBreadCrumb(route: ActivatedRoute, url: string = '', breadcrumbs: IBreadCrumb[] = []): IBreadCrumb[] {
    let label = this.getBreabcrumbLabel(route);
    let path = route.routeConfig?.path ? route.routeConfig.path : '';

    const lastRoutePart = path.split('/').pop() || '';
    const isDynamicRoute = lastRoutePart.startsWith(':');
    if (isDynamicRoute && !!route.snapshot) {
      const paramName = lastRoutePart.split(':')[1];
      path = path.replace(lastRoutePart, route.snapshot.params[paramName]);
      label = route.snapshot.params[paramName];
    }

    const nextUrl = path ? `${url}/${path}` : url;

    const breadcrumb: IBreadCrumb = {
      label,
      url: nextUrl,
    };

    if (breadcrumb.label) {
      breadcrumbs.push(breadcrumb);
    }

    if (route.firstChild) {
      return this.buildBreadCrumb(route.firstChild, nextUrl, breadcrumbs);
    }

    return breadcrumbs;
  }

  private getBreabcrumbLabel(route: ActivatedRoute): string {
    let label: string;

    if (route.routeConfig?.data?.['hidden'] === true) {
      label = '';
    } else if (!route.parent) {
      label = 'Home';
    } else if (route.routeConfig?.data) {
      label = route.routeConfig.data['breadcrumb'];
    } else {
      label = '';
    }

    return label;
  }
}
