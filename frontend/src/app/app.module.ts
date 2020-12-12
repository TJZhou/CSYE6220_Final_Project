import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';
import { JwtModule } from '@auth0/angular-jwt';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegisterComponent } from './components/register/register.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { LoginComponent } from './components/login/login.component';
import { ErrorComponent } from './components/error/error.component';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import { GroupComponent } from './components/group/group.component';
import { AccountComponent } from './components/account/account.component';
import { ExpenseDetailComponent } from './components/expense-detail/expense-detail.component';
import { IncomeDetailComponent } from './components/income-detail/income-detail.component';
import { HttpClientModule } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMomentDateModule, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { MainComponent } from './components/main/main.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTableModule } from '@angular/material/table';
import { HighchartsChartModule } from 'highcharts-angular';
import { BillComponent } from './components/bill/bill.component';
@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ErrorComponent,
    MainComponent,
    SideBarComponent,
    GroupComponent,
    AccountComponent,
    IncomeDetailComponent,
    ExpenseDetailComponent,
    BillComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatListModule,
    MatTableModule,
    MatExpansionModule,
    MatMomentDateModule,
    MatDividerModule,
    MatDatepickerModule,
    FormsModule,
    HighchartsChartModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('access_token');
        },
        allowedDomains: ['localhost:4200', 'localhost:8080']
      },
    }),
  ],
  providers: [
    { provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { useUtc: true } }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
