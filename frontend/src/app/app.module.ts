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
import { BillComponent } from './components/bill/bill.component';
import { AccountComponent } from './components/account/account.component';
import { ExpenseDetailComponent } from './components/expense-detail/expense-detail.component';
import { IncomeDetailComponent } from './components/income-detail/income-detail.component';
import { HttpClientModule } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MainComponent } from './components/main/main.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatTableModule } from '@angular/material/table';
import { HighchartsChartModule } from 'highcharts-angular';
@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ErrorComponent,
    MainComponent,
    SideBarComponent,
    BillComponent,
    AccountComponent,
    IncomeDetailComponent,
    ExpenseDetailComponent
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
    MatDividerModule,
    FormsModule,
    HighchartsChartModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('access_token');
        },
        allowedDomains: ['localhost:4200', 'localhost:8080']
        // disallowedRoutes: ["http://example.com/examplebadroute/"],
      },
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
