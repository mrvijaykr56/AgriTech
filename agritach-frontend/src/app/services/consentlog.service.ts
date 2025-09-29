import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Farmer {
  farmerId: number;
  name: string;
  mobile?: string;
  village?: string;
  consentGiven: boolean;
  consentTimestamp?: string;
}

export interface ConsentLog {
  id: number;
  consentValue: boolean;
  recordedAt: number;
  note?: string;
}

export interface ConsentDetails {
  farmer: Farmer;
  logs: ConsentLog[];
}

@Injectable({ providedIn: 'root' })
export class ConsentService {
  private baseUrl = 'http://localhost:8080/api/consent';

  constructor(private http: HttpClient) {}

  getConsentDetails(farmerId: number): Observable<ConsentDetails> {
    return this.http.get<ConsentDetails>(`${this.baseUrl}/${farmerId}`);
  }

recordConsent(farmerId: number, value: boolean, note?: string): Observable<string> {
  const params = {
    value: value.toString(),
    ...(note ? { note } : {}) // only include note if it's provided
  };

  return this.http.post(
    `${this.baseUrl}/${farmerId}/record`,
    null,
    {
      params,
      responseType: 'text' as 'text' // 👈 ensures response is treated as string
    }
  );
}
}