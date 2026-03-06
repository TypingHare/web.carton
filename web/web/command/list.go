package command

import (
	"fmt"
	"slices"
	"sort"

	"github.com/TypingHare/web.carton/v2026/web/web/share"
	"github.com/spf13/cobra"
)

func ListCommand(d share.WebDecorationLike) *cobra.Command {
	command := &cobra.Command{
		Use:   "list",
		Short: "Show all web records",
		RunE: func(cmd *cobra.Command, args []string) error {
			cabinet, err := share.GetWebCabinet(d)
			if err != nil {
				return fmt.Errorf("failed to get web cabinet: %w", err)
			}

			records := slices.Clone(cabinet.Objects)
			sort.Slice(records, func(i, j int) bool {
				return records[i].UpdatedAt > records[j].UpdatedAt
			})

			longestNameLength := 0
			for _, record := range records {
				if len(record.Name) > longestNameLength {
					longestNameLength = len(record.Name)
				}
			}

			for _, record := range records {
				cmd.Printf(
					"%-*s    %s\n",
					longestNameLength,
					record.Name,
					record.URL,
				)
			}

			return nil
		},
	}

	return command
}
