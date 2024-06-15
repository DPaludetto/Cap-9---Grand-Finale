import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";

import { AutoCompleteModule } from "primeng/autocomplete";
import { CalendarModule } from "primeng/calendar";
import { ChipsModule } from "primeng/chips";
import { ChipModule } from "primeng/chip";
import { DropdownModule } from "primeng/dropdown";
import { InputMaskModule } from "primeng/inputmask";
import { InputNumberModule } from "primeng/inputnumber";
import { CascadeSelectModule } from "primeng/cascadeselect";
import { MultiSelectModule } from "primeng/multiselect";
import { InputTextareaModule } from "primeng/inputtextarea"; 
import { InputTextModule } from "primeng/inputtext";
import { RatingModule } from 'primeng/rating';
import { KnobModule } from 'primeng/knob';
import { ListboxModule } from 'primeng/listbox';
import { SelectButtonModule } from 'primeng/selectbutton';
import { CheckboxModule } from 'primeng/checkbox';
import { ButtonModule } from 'primeng/button';
import { InputSwitchModule } from 'primeng/inputswitch';
import { RadioButtonModule } from 'primeng/radiobutton';
import { ColorPickerModule } from 'primeng/colorpicker';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { SliderModule } from 'primeng/slider';



import { BreadcrumbComponent } from "./breadcrumb/breadcrumb.component";
import { CalendarButtonComponent } from "./calendar-button/calendar-button.component";
import { CalendarComponent } from "./calendar/calendar.component";
import { ConfirmationModalComponent } from "./confirmation-modal/confirmation-modal.component";
import { DataTableComponent } from "./data-table/data-table.component";
import { InputPasswordComponent } from "./input-password/input-password.component";
import { FormInputComponent } from "./form-input/form-input.component";
import { ListPageTemplateComponent } from "./list-page-template/list-page-template.component";
import { PageSectionTemplateComponent } from "./page-section-template/page-section-template.component";
import { PageTemplateComponent } from "./page-template/page-template.component";
import { PicklistComponent } from "./picklist/picklist.component";
import { SelectComponent } from "./select/select.component";
import { SimpleCrudTemplateComponent } from "./simple-crud-template/simple-crud-template.component";
import { ToastComponent } from "./toast/toast.component";
import { DialogModule } from "primeng/dialog";
import { PanelMenuModule } from "primeng/panelmenu";
import { PickListModule } from "primeng/picklist";
import { TableModule } from "primeng/table";
import { TooltipModule } from "primeng/tooltip";
import { TabViewModule } from 'primeng/tabview';
import {TimelineModule} from 'primeng/timeline';
import { DynamicDialogModule } from 'primeng/dynamicdialog';

import { MatIconModule } from '@angular/material/icon';
import { TimelineComponent } from './timeline/timeline.component';
import { FileUploadModule } from "primeng/fileupload";
import { ImageModule } from "primeng/image";
import { PanelModule } from "primeng/panel";
import { DividerModule } from "primeng/divider";
import { RxReactiveFormsModule } from "@rxweb/reactive-form-validators";
import { TagModule } from "primeng/tag";
import { ContextMenuModule } from "primeng/contextmenu";
import { MenuModule } from "primeng/menu";

// import { MatDatepickerModule } from '@angular/material/datepicker';
// import { MatNativeDateModule } from "@angular/material/core";
// import { MatPaginatorModule } from '@angular/material/paginator';
// import { MatSlideToggleModule } from '@angular/material/slide-toggle';
// import {MatToolbarModule} from '@angular/material/toolbar';
// import {MatSidenavModule} from '@angular/material/sidenav'
// import { MatTableModule } from '@angular/material/table';


@NgModule({
  declarations: [
    PageTemplateComponent,
    PageSectionTemplateComponent,
    InputPasswordComponent,
    BreadcrumbComponent,
    FormInputComponent,
    SelectComponent,
    DataTableComponent,
    SimpleCrudTemplateComponent,
    ToastComponent,
    CalendarButtonComponent,
    ConfirmationModalComponent,
    CalendarComponent,
    PicklistComponent,
    FormInputComponent,
    ListPageTemplateComponent,
    TimelineComponent,
    
    
    
  ],
  imports: [
    PanelMenuModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    ListboxModule,
    MultiSelectModule,
    TableModule,
    ButtonModule,
    DropdownModule,
    CalendarModule,
    InputTextareaModule,
    TooltipModule,
    CheckboxModule,
    InputNumberModule,
    PickListModule,
    RadioButtonModule,
    DialogModule,
    AutoCompleteModule,
    ChipsModule,
    ChipModule,
    InputMaskModule,
    CascadeSelectModule,
    InputTextModule,
    RatingModule,
    KnobModule,
    SelectButtonModule,
    InputSwitchModule,
    ColorPickerModule,
    ToggleButtonModule,
    SliderModule,    
    MatIconModule,
    TabViewModule,
    TimelineModule,
    DynamicDialogModule,
    FileUploadModule,
    ImageModule,
    PanelModule,
    DividerModule,
    FormsModule, 
    ReactiveFormsModule,
    RxReactiveFormsModule,
    TagModule,
    FormsModule,
    ContextMenuModule,
    MenuModule
    

    
    
    // MatDatepickerModule,
    // MatTableModule,
    // MatPaginatorModule,
    // MatSlideToggleModule,
    // MatNativeDateModule,
    // MatSidenavModule,
    // MatToolbarModule,
  ],
  exports: [
    PanelMenuModule,
    ReactiveFormsModule,
    PageTemplateComponent,
    PageSectionTemplateComponent,
    BreadcrumbComponent,
    InputPasswordComponent,
    FormInputComponent,
    SelectComponent,
    FormInputComponent,
    DataTableComponent,
    SimpleCrudTemplateComponent,
    ToastComponent,
    PicklistComponent,
    ListboxModule,
    MultiSelectModule,
    CalendarButtonComponent,
    ConfirmationModalComponent,
    CalendarModule,
    CalendarComponent,
    TimelineComponent,
    InputTextareaModule,
    TooltipModule,
    InputNumberModule,
    DropdownModule,
    CheckboxModule,
    PickListModule,
    TableModule,
    RadioButtonModule,
    DialogModule,
    ListPageTemplateComponent,
    AutoCompleteModule,
    ChipsModule,
    ChipModule,
    InputMaskModule,
    CascadeSelectModule,
    InputTextModule,
    RatingModule,
    KnobModule,
    SelectButtonModule,
    InputSwitchModule,
    ColorPickerModule,
    ToggleButtonModule,
    SliderModule,
    MatIconModule,
    TabViewModule,
    TimelineModule,
    DynamicDialogModule,
    FileUploadModule,
    ImageModule,
    PanelModule,
    DividerModule,
    ReactiveFormsModule,
    RxReactiveFormsModule,
    TagModule,
    FormsModule,
    ContextMenuModule,
    MenuModule
    // MatDatepickerModule,
    // MatTableModule,
    // MatPaginatorModule,
    // MatSlideToggleModule,
    // MatNativeDateModule,
    // MatSidenavModule,
    // MatToolbarModule,
  ],


})
export class CoreComponentModule { }
