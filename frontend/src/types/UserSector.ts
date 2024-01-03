import {Sector} from "./Sector";

export interface UserSector {
    name: string,
    sectors: Sector[],
    agreeToTerms: boolean
}