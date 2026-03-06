package share

import (
	"fmt"
	"strconv"
	"time"
)

// WebRecord.
type WebRecord struct {
	Name      string
	URL       string
	CreatedAt int64
	UpdatedAt int64
}

func NewWebRecord(name string, url string) WebRecord {
	now := time.Now().Unix()
	return WebRecord{
		Name:      name,
		URL:       url,
		CreatedAt: now,
		UpdatedAt: now,
	}
}

func SerializeWebRecord(record WebRecord) []string {
	return []string{
		record.Name,
		record.URL,
		strconv.FormatInt(record.CreatedAt, 10),
		strconv.FormatInt(record.UpdatedAt, 10),
	}
}

func DeserializeWebRecord(items []string) (WebRecord, error) {
	if len(items) != 4 {
		return WebRecord{}, fmt.Errorf(
			"not enough items; expected 4 but got %d", len(items),
		)
	}

	name := items[0]
	url := items[1]
	createdAt, err := strconv.ParseInt(items[2], 10, 64)
	if err != nil {
		return WebRecord{}, fmt.Errorf(
			"failed to parse createdAt",
		)
	}
	updatedAt, err := strconv.ParseInt(items[3], 10, 64)
	if err != nil {
		return WebRecord{}, fmt.Errorf(
			"failed to parse updatedAt",
		)
	}

	return WebRecord{
		Name:      name,
		URL:       url,
		CreatedAt: createdAt,
		UpdatedAt: updatedAt,
	}, nil
}
