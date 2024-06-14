import { HoursFormatPipe } from '@shared/pipes/hours-format.pipe';
import * as moment from 'moment';

export class TimeConverterUtils {
  static HOURS_PIPE_FORMAT = new HoursFormatPipe();

  static millisToHours(millis: number): string {
    return moment.utc(millis).format('HH:mm');
  }

  static hoursToMillis(hours: string): number {
    return moment.duration(hours).asMilliseconds();
  }

  static millisToDurationHours(millis: number): string {
    const duration = moment.duration(millis, 'ms');
    const hours = duration.asHours();
    const minutes = duration.minutes();

    return `${String(Math.floor(hours)).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`;
  }

  static convertUsingHoursPipe(value: number | string, showSignal?: boolean): string {
    return this.HOURS_PIPE_FORMAT.transform(value, showSignal);
  }

  static convertHoursToMilliseconds(value: number): number {
    return moment.duration(value, 'h').asMilliseconds();
  }

  static convertMillisecondsToHours(value: number): number {
    return moment.duration(value, 'ms').asHours();
  }
}
