export interface Farmer {
  farmerId?: number;          // use number since it's a Long in Java
  name: string;
  mobile?: string;
  village?: string;
  consentGiven: boolean;     // match Java field name
  consentTimestamp?: string; // Instant is serialized as ISO string (e.g. "2025-09-28T14:09:00Z")
}