export interface Inspection {
  inspectionId: number;   // required, numeric
  fieldId: number;
  commodity: string;
  label: string;
  confidence: number;
  riskLevel: string;
  latitude?: number;
  longitude?: number;
  createdAt: number;
}