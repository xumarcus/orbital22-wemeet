import {
  getInfoSummary,
  getSelfInfoColor
} from '../../../components/schedule/appearance'
import { SYSTEM_THEME, TEXT } from '../../../core/const'
import { describe, expect, it } from '@jest/globals'

describe('Schedule appearance', () => {
  describe('event footer', () => {
    it('should render correctly without data', () => {
      expect(getInfoSummary([])).toEqual(TEXT.INFO_SUMMARY.NONE)
    })

    it('should render correctly with single entry', () => {
      expect(getInfoSummary([
        {
          available: true,
          user: {
            id: 1,
            email: 'test@test.com'
          },
          rank: 1
        }])).toEqual('test@test.com\n' +
        'is picking this')
    })

    it('should render correctly with multiple entries', () => {
      expect(getInfoSummary([
        {
          available: false,
          user: {
            id: 1,
            email: 'test@test.com'
          },
          rank: null
        },
        {
          available: true,
          user: {
            id: 2,
            email: 'another-test@test.com'
          },
          rank: 1
        },
        {
          available: true,
          user: {
            id: 3,
            email: 'yet-another-test@test.com'
          },
          rank: 4
        }])).toEqual('another-test@test.com\n' +
        'and 1 more...')
    })
  })

  describe('event color', () => {
    it('should render correctly without data', () => {
      expect(getSelfInfoColor({ id: 1 }, []))
        .toEqual(SYSTEM_THEME.palette.primary.main)
    })

    it('should render correctly with ranks', () => {
      expect(getSelfInfoColor({ id: 1 }, [
        {
          available: true,
          user: {
            id: 1,
            email: 'test@test.com'
          },
          rank: 1
        }])).toEqual(SYSTEM_THEME.palette.warning.light)

      expect(getSelfInfoColor({ id: 1 }, [
        {
          available: true,
          user: {
            id: 1,
            email: 'test@test.com'
          },
          rank: 2
        }])).toEqual(SYSTEM_THEME.palette.warning.main)

      expect(getSelfInfoColor({ id: 1 }, [
        {
          available: true,
          user: {
            id: 1,
            email: 'test@test.com'
          },
          rank: 3
        }])).toEqual(SYSTEM_THEME.palette.warning.dark)

      expect(getSelfInfoColor({ id: 1 }, [
        {
          available: true,
          user: {
            id: 1,
            email: 'test@test.com'
          },
          rank: 4
        }])).toEqual(SYSTEM_THEME.palette.error.main)
    })

    it('should render correctly with multiple entries', () => {
      expect(getSelfInfoColor({ id: 1 }, [
        {
          available: false,
          user: {
            id: 1,
            email: 'test@test.com'
          },
          rank: null
        },
        {
          available: true,
          user: {
            id: 2,
            email: 'another-test@test.com'
          },
          rank: 1
        },
        {
          available: true,
          user: {
            id: 3,
            email: 'yet-another-test@test.com'
          },
          rank: 4
        }]))
        .toEqual(SYSTEM_THEME.palette.primary.main)
    })
  })
})
