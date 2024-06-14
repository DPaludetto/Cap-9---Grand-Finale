import { DatePipe } from '@angular/common';
import { Moment } from 'moment';
import * as moment from 'moment';

export class DateUtils {


  static isToday(date: Date): boolean {
    return moment(date).isSame(DateUtils.today, 'd');
  }

  static isYesterday(date: Date): boolean {
    return moment(date).isSame(DateUtils.today.subtract(1, 'd'), 'd');
  }

  static isTomorrow(date: Date): boolean {
    return moment(date).isSame(DateUtils.today.add(1, 'd'), 'd');
  }

  static isBeforeToday(date: Date): boolean {
    return moment(date).isBefore(DateUtils.today, 'd');
  }

  static isFirstDateAfterSecond(firstDate: Date, secondDate: Date): boolean {
    return moment(firstDate).isAfter(secondDate);
  }

  static isFirstDateBeforeSecond(firstDate: Date, secondDate: Date): boolean {
    return moment(firstDate).isBefore(secondDate);
  }

  static isAfterToday(date: Date): boolean {
    return moment(date).isAfter(DateUtils.today.endOf('day'), 'd');
  }

  static get today(): Moment {
    return moment(new Date()).startOf('day');
  }

  static isWeekend(date: Date): boolean {
    return moment(date).day() === 0 || moment(date).day() === 6;
  }

  static isNotWeekend(date: Date): boolean {
    return !DateUtils.isWeekend(date);
  }

  static dateToStringFormat(date: Date): string {
    return moment(date).format('YYYY-MM-DD');
  }

  static dateToStringFormatPresentation(date: Date): string {

    return date !== null ?moment(date).format('DD/MM/yyyy') : '-';
  }

  static dateToStringFullFormatPresentation(date: Date | undefined): string {
    return moment(date).format('DD/MM/yyyy HH:mm:ss');
  }

  static toLocaleDate(str: string): string{
    return new Date(str).toLocaleDateString();
  }
  static toLocale(str: any): string{
    return new Date(str).toLocaleString();
  }
  
}
